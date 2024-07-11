package example.com

import example.com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()
}

val eventos = mutableListOf(
    Evento("Reunião", "Reunião de status semanal", 1, 60, "10:00", "11:00", DiaDaSemana.SEGUNDA, emptyList()),
    Evento("Gincana", "Reunião de status semanal", 1, 60, "10:00", "11:00", DiaDaSemana.TERCA, emptyList()),
    Evento("Apresentação", "Demonstração do produto", 2, 45, "14:00", "14:45", DiaDaSemana.QUARTA, emptyList()),
    Evento("Apresentação", "Demonstração do produto", 2, 45, "14:00", "14:45", DiaDaSemana.QUINTA, emptyList()),
    Evento("Reunião", "Reunião de status semanal", 1, 60, "10:00", "11:00", DiaDaSemana.SEXTA, emptyList()),
    Evento("Apresentação", "Demonstração do produto", 2, 45, "14:00", "14:45", DiaDaSemana.SEXTA, emptyList())
    )

fun findEventoById(eventos: List<Evento>, id: Int): Evento? {
    return eventos.find { it.id == id }
}
