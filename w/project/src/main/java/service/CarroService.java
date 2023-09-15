package service;

import dao.CarroDAO;
import model.Carro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarroService {

    private CarroDAO carroDAO = new CarroDAO();

    public ModelAndView index(Request request, Response response) {
        List<Carro> carros = carroDAO.getAll();
        Map<String, Object> model = new HashMap<>();
        model.put("carros", carros);
        return new ModelAndView(model, "templates/carros.vm");
    }

    public ModelAndView insert(Request request, Response response) {
        Carro carro = new Carro();
        carro.setMarca(request.queryParams("marca"));
        carro.setModelo(request.queryParams("modelo"));
        carro.setAno(Integer.parseInt(request.queryParams("ano")));

        if (carroDAO.insert(carro)) {
            response.status(201);
        } else {
            response.status(500);
        }
        return index(request, response);
    }

    public ModelAndView update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Carro existingCarro = carroDAO.get(id);

        if (existingCarro != null) {
            existingCarro.setMarca(request.queryParams("marca"));
            existingCarro.setModelo(request.queryParams("modelo"));
            existingCarro.setAno(Integer.parseInt(request.queryParams("ano")));

            if (carroDAO.update(existingCarro)) {
                response.status(200);
            } else {
                response.status(500);
            }
        } else {
            response.status(404);
        }
        return index(request, response);
    }

    public ModelAndView delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Carro existingCarro = carroDAO.get(id);

        if (existingCarro != null) {
            if (carroDAO.delete(id)) {
                response.status(200);
            } else {
                response.status(500);
            }
        } else {
            response.status(404);
        }
        return index(request, response);
    }

    public ModelAndView edit(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Carro carro = carroDAO.get(id);

        if (carro != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("carro", carro);
            return new ModelAndView(model, "templates/edit.vm");
        } else {
            response.status(404);
            return index(request, response);
        }
    }
}

