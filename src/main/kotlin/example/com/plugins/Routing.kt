package example.com

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import io.ktor.server.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")
        // Redireciona a rota raiz para o arquivo estático index.html
        get("/") {
            call.respondRedirect("/static/index.html")
        }

        // Rotas da API
        get("/api/eventos") {
            println("Received GET request at /api/eventos")
            withContext(Dispatchers.IO) {
                val eventos = transaction {
                    EventosTable.selectAll().map {
                        Evento(
                            id = it[EventosTable.id],
                            titulo = it[EventosTable.titulo],
                            descricao = it[EventosTable.descricao],
                            duracao = it[EventosTable.duracao],
                            horarioInicio = it[EventosTable.horarioInicio],
                            horarioTermino = it[EventosTable.horarioTermino],
                            diaDaSemana = it[EventosTable.diaDaSemana],
                            membros = emptyList()
                        )
                    }
                }
                call.respond(eventos)
            }
        }

        get("/api/membros") {
            println("Received GET request at /api/membros")
            withContext(Dispatchers.IO) {
                val membros = transaction {
                    MembrosTable.selectAll().map {
                        Membro(
                            id = it[MembrosTable.id],
                            nome =  it[MembrosTable.nome],
                            trabalho = it[MembrosTable.trabalho],
                            eventos = emptyList()
                        )
                    }
                }
                call.respond(membros)
            }
        }


        post("/api/eventos") {
            val newEvento = call.receive<Evento>()
            addEvento(newEvento.titulo, newEvento.descricao, newEvento.duracao, newEvento.horarioInicio, newEvento.horarioTermino, newEvento.diaDaSemana)
            call.respondText("Evento adicionado com sucesso", status = HttpStatusCode.Created)
        }

        post("/api/membros") {
            val newMembro = call.receive<Membro>()
            addMembro(newMembro.nome, newMembro.trabalho)
            call.respondText("Membro adicionado com sucesso", status = HttpStatusCode.Created)
        }


        get("/api/eventos/{eventoId}") {
            val eventoId = call.parameters["eventoId"]?.toIntOrNull()
            if (eventoId != null) {
                val evento = withContext(Dispatchers.IO) {
                    transaction {
                        EventosTable.select { EventosTable.id eq eventoId }.singleOrNull()?.let {
                            Evento(
                                id = it[EventosTable.id],
                                titulo = it[EventosTable.titulo],
                                descricao = it[EventosTable.descricao],
                                duracao = it[EventosTable.duracao],
                                horarioInicio = it[EventosTable.horarioInicio],
                                horarioTermino = it[EventosTable.horarioTermino],
                                diaDaSemana = it[EventosTable.diaDaSemana]
                            )
                        }
                    }
                }

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
            if (eventoId != null) {
                val deletedRows = withContext(Dispatchers.IO) {
                    transaction {
                        EventosTable.deleteWhere { EventosTable.id eq eventoId }
                    }
                }

                if (deletedRows > 0) {
                    call.respondText("Evento deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Evento não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
            }
        }

        delete("/api/membros/{membroId}") {
            val membroId = call.parameters["membroId"]?.toIntOrNull()
            if (membroId != null) {
                val deletedRows = withContext(Dispatchers.IO) {
                    transaction {
                        MembrosTable.deleteWhere { MembrosTable.id eq membroId }
                    }
                }

                if (deletedRows > 0) {
                    call.respondText("Membro deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Membro não encontrado", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
