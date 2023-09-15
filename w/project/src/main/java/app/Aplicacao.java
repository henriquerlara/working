package app;

import service.CarroService;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class Aplicacao {

    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");

        CarroService carroService = new CarroService();

        get("/carros", (request, response) -> {
            return carroService.index(request, response);
        }, new VelocityTemplateEngine());

        post("/carros", (request, response) -> {
            return carroService.insert(request, response);
        }, new VelocityTemplateEngine());

        get("/carros/:id/edit", (request, response) -> {
            return carroService.edit(request, response);
        }, new VelocityTemplateEngine());

        post("/carros/:id/edit", (request, response) -> {
            return carroService.update(request, response);
        }, new VelocityTemplateEngine());

        post("/carros/:id/delete", (request, response) -> {
            return carroService.delete(request, response);
        }, new VelocityTemplateEngine());
    }
}

