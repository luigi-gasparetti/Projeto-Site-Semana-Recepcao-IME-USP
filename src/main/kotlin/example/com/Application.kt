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
<<<<<<< Updated upstream
    Membro(nome = "João", id = 0, trabalho = 40, eventos = emptyList()),
=======
    Membro(nome = "João", ,trabalho = 40, eventos = emptyList()),
    Membro(nome = "Maria", trabalho = 32, eventos = emptyList()),
    Membro(nome = "Carlos", trabalho = 45, eventos = emptyList()),
    Membro(nome = "Ana", trabalho = 28, eventos = emptyList()),
    Membro(nome = "teste", trabalho = 280, eventos = emptyList()),
    Membro(nome = "teste2", trabalho = 280, eventos = emptyList())
>>>>>>> Stashed changes
)

// Create Evento instances and associate Membro instances with them
val eventos = mutableListOf(
    Evento("Reunião", "Reunião de status semanal", 0, 60, "10:00", "11:00", DiaDaSemana.SEGUNDA, emptyList()),
)

fun findEventoById(eventos: List<Evento>, id: Int): Evento? {
    return eventos.find { it.id == id }
}

