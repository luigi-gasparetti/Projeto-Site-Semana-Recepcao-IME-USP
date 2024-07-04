package example.com

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")

        get("/") {
        call.respondRedirect("/static/index.html")
        }

        get("/static/eventos.html/{eventoId}") {
            val eventoId = call.parameters["eventoId"]?.toIntOrNull()

            if (eventoId != null && eventoId >= 0) {
                val evento = findEventoById(eventos, eventoId)

                if (evento != null) {
                    call.respond(evento)
                } else {
                    call.respondText("Evento não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
            }
        }

    }

}

