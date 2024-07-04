package example.com

import kotlinx.serialization.Serializable
import java.time.LocalTime

enum class DiaDaSemana(val nome: String) {
    SEGUNDA("Segunda-feira"),
    TERCA("Terça-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira");

    override fun toString(): String {
        return nome
    }
}

open class Evento(
    val titulo: String,
    val descricao: String,
    val duracao: Int, // duração em minutos
    val horarioInicio: LocalTime,
    val horarioTermino: LocalTime,
    val diaDaSemana: DiaDaSemana,
    var membros: List<Membro>
) {
    fun adicionarMembro(membro: Membro) {
        if (!membros.contains(membro)) {
            membros = membros + membro
            membro.adicionarEvento(this)
        }
    }

    override fun toString(): String {
        return "Evento(titulo='$titulo', descricao='$descricao', duracao=$duracao, horarioInicio=$horarioInicio, horarioTermino=$horarioTermino, diaDaSemana=$diaDaSemana, membros=${membros.map { it.nome }})"
    }
}

open class Membro(
    val nome: String, // nome do membro
    val id: Int,
    var trabalho: Int, // duração de horas trabalhadas em minutos
    var eventos: List<Evento>
) {
    fun adicionarEvento(evento: Evento) {
        if (!eventos.contains(evento)) {
            eventos = eventos + evento
            evento.adicionarMembro(this)
        }
    }

    override fun toString(): String {
        return "Membro(nome='$nome', id=$id, trabalho=$trabalho, eventos=${eventos.map { it.titulo }})"
    }
}


fun main() {
    // Criando alguns membros sem eventos inicialmente
    val joao = Membro(nome = "João", id = 1, trabalho = 480, eventos = emptyList()) // 8 horas
    val maria = Membro(nome = "Maria", id = 2, trabalho = 300, eventos = emptyList()) // 5 horas
    val carlos = Membro(nome = "Carlos", id = 3, trabalho = 600, eventos = emptyList()) // 10 horas

    // Criando um evento com esses membros
    val evento1 = Evento(
        titulo = "Reunião de Planejamento",
        descricao = "Discussão sobre planejamento semanal",
        duracao = 90,
        horarioInicio = LocalTime.of(9, 0),
        horarioTermino = LocalTime.of(10, 30),
        diaDaSemana = DiaDaSemana.SEGUNDA,
        membros = emptyList()
    )

    // Adicionando o evento aos membros
    evento1.adicionarMembro(joao)
    evento1.adicionarMembro(maria)
    evento1.adicionarMembro(carlos)

    // Imprimindo os detalhes dos membros e eventos
    println("Membros e seus eventos:")
    println(joao)
    println(maria)
    println(carlos)

    // Imprimindo detalhes do evento
    println("")
    println("Detalhes do evento:")
    println(evento1)
}