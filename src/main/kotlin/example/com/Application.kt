package example.com

import example.com.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*


import kotlinx.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()

}

// Create Membro instances
val membros = mutableListOf(
    Membro(nome = "João", id = 0, trabalho = 40, eventos = emptyList()),
)




// Create Evento instances and associate Membro instances with them
/*val eventos = mutableListOf(
    Evento("Reunião", "Reunião de status semanal", 0, 60, "10:00", "11:00", DiaDaSemana.SEGUNDA, emptyList()),
) */





// Create Evento instances and associate Membro instances with them




fun findEventoById(eventos: List<Evento>, id: Int): Evento? {
    return eventos.find { it.id == id }
}

