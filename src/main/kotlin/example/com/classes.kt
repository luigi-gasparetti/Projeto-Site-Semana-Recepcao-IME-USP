package example.com

import kotlinx.serialization.Serializable
import java.time.LocalTime

enum class DiaDaSemana(val nome: String) {
    SEGUNDA("Segunda-feira"),
    TERÇA("Terça-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira");

    override fun toString(): String {
        return nome
    }
}

@Serializable
open class Evento(
    val titulo: String,
    val descricao: String,
    val id: Int,
    val duracao: Int, // duração em minutos
    val horarioInicio: String,
    val horarioTermino: String,
    val diaDaSemana: String,
    var membros: List<Membro> = emptyList()
) {
    fun adicionarMembro(membro: Membro) {
        if (!membros.contains(membro)) {
            membros = membros + membro
            // membro.adicionarEvento(this)
        }
    }

    override fun toString(): String {
        return "Evento(titulo='$titulo', descricao='$descricao', id='$id',duracao=$duracao, horarioInicio=$horarioInicio, horarioTermino=$horarioTermino, diaDaSemana=$diaDaSemana, membros=${membros.map { it.nome }})"
    }
}

@Serializable
open class Membro(
    val nome: String, // nome do membro
    val id: Int,
    var trabalho: Int, // duração de horas trabalhadas em minutos
    var eventos: List<Evento> = emptyList()
) {
    fun adicionarEvento(evento: Evento) {
        if (!eventos.contains(evento)) {
            eventos = eventos + evento
            // evento.adicionarMembro(this)
            this.adicionarTrabalho(evento.duracao)
        }
    }

    fun adicionarTrabalho(minutos: Int){
        this.trabalho = this.trabalho + minutos
    }

    override fun toString(): String {
        return "Membro(nome='$nome',id'=$id, trabalho=$trabalho, eventos=${eventos.map { it.titulo }})"
    }
}

/* fun main() {
    // Criando alguns membros sem eventos inicialmente
    val joao = Membro(nome = "João", trabalho = 0, eventos = emptyList()) // 8 horas
    val maria = Membro(nome = "Maria", trabalho = 0, eventos = emptyList()) // 5 horas
    val carlos = Membro(nome = "Carlos", trabalho = 0, eventos = emptyList()) // 10 horas

    // Criando um evento com esses membros
    val evento1 = Evento(
        titulo = "Reunião de Planejamento",
        descricao = "Discussão sobre planejamento semanal",
        id = 1,
        duracao = 90,
        horarioInicio = "09:00",
        horarioTermino = "10:30",
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
} */