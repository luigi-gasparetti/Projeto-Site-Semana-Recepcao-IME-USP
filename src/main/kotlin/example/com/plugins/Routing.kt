package example.com

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.*

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")





        get("/") {
        call.respondRedirect("/static/index.html")
        }

        get("/api/eventos") {
            call.respond(eventos)
        }

        get("/api/membros") {
            call.respond(membros)
        }


        post("/api/eventos") {
                val newEvento = call.receive<Evento>()
                eventos.add(newEvento)
                call.respondText("Evento adicionado com sucesso", status = HttpStatusCode.Created)
        }

        get("/api/eventos/{eventoId}") {
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

        delete("/api/eventos/{eventoId}") {
            val eventoId = call.parameters["eventoId"]?.toIntOrNull()
            if (eventoId != null && eventoId >= 0) {
                val eventoRemoved = eventos.removeIf { it.id == eventoId }
                if (eventoRemoved) {
                    call.respondText("Evento deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Evento não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
            }
        }

        get("/api/membros") {
            call.respond(membros)
        }

        post("/api/membros") {
            val newMember = call.receive<Membro>()
            println("Received member: $newMember")
            membros.add(newMember)
            call.respondText("Membro adicionado com sucesso", status = HttpStatusCode.Created)
        }

        delete("/api/membros/{nome}") {
            val nome = call.parameters["nome"]
            if (nome != null) {
                val membroRemoved = membros.removeIf { it.nome == nome }
                if (membroRemoved) {
                    call.respondText("Membro deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Membro não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Nome do membro é necessário", status = HttpStatusCode.BadRequest)
            }
        }

    }

}

