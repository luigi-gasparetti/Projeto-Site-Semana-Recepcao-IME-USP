package example.com

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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
    val url = "jdbc:postgresql://localhost:5432/comisys"
    val driver = "org.postgresql.Driver"
    val user = "luigi"
    val password = "senha"

    Database.connect(url, driver = driver, user = user, password = password)

    transaction {
        SchemaUtils.create(MembrosTable, EventosTable, EventoMembroTable)
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
                it[diaDaSemana] = "SEGUNDA"
            }
        }
    }
}
