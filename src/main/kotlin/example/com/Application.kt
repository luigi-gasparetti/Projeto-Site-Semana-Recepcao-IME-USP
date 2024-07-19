package example.com

import example.com.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*



import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop



object MembrosTable : Table() {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 50)
    val trabalho = integer("trabalho")
    override val primaryKey = PrimaryKey(id)
}

object EventosTable : Table() {
    val id = integer("id").autoIncrement()
    val titulo = varchar("titulo", 50)
    val descricao = varchar("descricao", 255)
    val duracao = integer("duracao")
    val horarioInicio = varchar("horarioInicio", 5)
    val horarioTermino = varchar("horarioTermino", 5)
    val diaDaSemana = varchar("diaDaSemana", 15)
    override val primaryKey = PrimaryKey(id)
}

object EventoMembroTable : Table() {
    val eventoId = integer("eventoId").references(EventosTable.id)
    val membroId = integer("membroId").references(MembrosTable.id)
}

fun Application.configureDatabases() {
    val url = "jdbc:postgresql://localhost:5432/bd_projeto"
    val driver = "org.postgresql.Driver"
    val user = "pedro"
    val password = "senha_teste" // The new password you set

    Database.connect(url, driver = driver, user = user, password = password)

    transaction {
        SchemaUtils.create(MembrosTable, EventosTable)
    }
}

fun addMembro(nome: String, trabalho: Int) {
    transaction {
        MembrosTable.insert {
            it[MembrosTable.nome] = nome
            it[MembrosTable.trabalho] = trabalho
        }
    }
}



fun addEvento(titulo: String, descricao: String, duracao: Int, horarioInicio: String, horarioTermino: String, diaDaSemana: String) {
    transaction {
        EventosTable.insert {
            it[EventosTable.titulo] = titulo
            it[EventosTable.descricao] = descricao
            it[EventosTable.duracao] = duracao
            it[EventosTable.horarioInicio] = horarioInicio
            it[EventosTable.horarioTermino] = horarioTermino
            it[EventosTable.diaDaSemana] = diaDaSemana
        }
    }
}

fun initializeDatabase() {
    transaction {
        // Check if the tables are empty before inserting
        if (MembrosTable.selectAll().empty()) {
            // Insert initial members
            MembrosTable.insert {
                it[nome] = "João"
                it[trabalho] = 40
            }
            MembrosTable.insert {
                it[nome] = "Maria"
                it[trabalho] = 30
            }
        }

        if (EventosTable.selectAll().empty()) {
            // Insert initial events
            EventosTable.insert {
                it[titulo] = "Reunião de Planejamento"
                it[descricao] = "Discussão sobre planejamento semanal"
                it[duracao] = 90
                it[horarioInicio] = "09:00"
                it[horarioTermino] = "10:30"
                it[diaDaSemana] = "Segunda-feira"
            }
        }
    }
}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()
    initializeDatabase()

}

// Create Membro instances
val membros = mutableListOf(
    Membro(nome = "João", id = 0, trabalho = 40, eventos = emptyList()),
)




// Create Evento instances and associate Membro instances with them
val eventos = mutableListOf(
    Evento("Reunião", "Reunião de status semanal", 0, 60, "10:00", "11:00", DiaDaSemana.SEGUNDA, emptyList()),
)





// Create Evento instances and associate Membro instances with them




fun findEventoById(eventos: List<Evento>, id: Int): Evento? {
    return eventos.find { it.id == id }
}

