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

// Create Membro instances
val membros = mutableListOf(
    Membro(nome = "João", id = 0, trabalho = 40, eventos = emptyList()),
)

// Create Evento instances and associate Membro instances with them
val eventos = mutableListOf(
    Evento("Reunião", "Reunião de status semanal", 1, 60, "10:00", "11:00", DiaDaSemana.SEGUNDA, emptyList()),
    Evento("Gincana", "Gincana de integração", 2, 90, "15:00", "16:30", DiaDaSemana.TERÇA, emptyList()),
    Evento("Apresentação", "Demonstração do produto", 3, 45, "14:00", "14:45", DiaDaSemana.QUARTA, emptyList()),
    Evento("Workshop", "Workshop de desenvolvimento", 4, 120, "10:00", "12:00", DiaDaSemana.QUINTA, emptyList()),
    Evento("Reunião", "Reunião de status semanal", 5, 60, "10:00", "11:00", DiaDaSemana.SEXTA, emptyList()),
    Evento("Apresentação", "Demonstração do produto", 6, 45, "14:00", "14:45", DiaDaSemana.SEXTA, emptyList())
)

fun findEventoById(eventos: List<Evento>, id: Int): Evento? {
    return eventos.find { it.id == id }
}

fun findMembroById(membros: List<Membro>, id: Int): Membro? {
    return membros.find { it.id == id }
}
