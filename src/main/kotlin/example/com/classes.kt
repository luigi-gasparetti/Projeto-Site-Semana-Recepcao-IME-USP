package example.com

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

data class Evento(
    val titulo: String,
    val descricao: String,
    val duracao: Int, // duração em minutos
    val horarioInicio: LocalTime,
    val horarioTermino: LocalTime,
    val diaDaSemana: DiaDaSemana,
    val membros: List<Membro>
)

data class Membro(
    val nome: String, // nome do membro
    val id: Int,
    val trabalho: Int, // duração de horas trabalhadas em minutos
    val eventos: List<Evento>
)

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
        membros = listOf(joao, maria, carlos)
    )

    // Adicionando o evento aos membros
    val joaoComEventos = joao.copy(eventos = listOf(evento1))
    val mariaComEventos = maria.copy(eventos = listOf(evento1))
    val carlosComEventos = carlos.copy(eventos = listOf(evento1))

    // Imprimindo os detalhes dos membros e eventos
    println("Membros e seus eventos:")
    println(joaoComEventos)
    println(mariaComEventos)
    println(carlosComEventos)

    // Imprimindo detalhes do evento
    println("")
    println("Detalhes do evento:")
    println(evento1)
}