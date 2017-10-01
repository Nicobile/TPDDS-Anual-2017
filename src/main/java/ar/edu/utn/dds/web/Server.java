package ar.edu.utn.dds.web;

import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Server {
	public static void main(String[] args) {
	
		Spark.get("/", (req,res)-> {
			return "<h1> ¿Donde invierto? <h1>";
		});

	}
}
