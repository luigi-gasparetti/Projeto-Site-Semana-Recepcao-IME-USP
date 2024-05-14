abstract class User(nome: String, curso: String, nusp: Int){

    abstract fun bemVindo()
}

class Calouro(nome: String, curso: String, nusp: Int, email_usp: String, time_gincana: String): User(nome, curso, nusp) {


    override fun bemVindo() {
        //to do
    }
}

class Time(time_gincana: String, membros: Array<Calouro>, pontos: Int){

    var pontos: Int = 0
        get() = field
        set(value) {
            field = value
        }
}

class MembroComissao(nome: String, curso: String, nusp: Int, horas_trab: Int, atividades_inscritas: Array<String>, fl_presidente: Boolean): User(nome, curso, nusp) {

    var atividades_inscritas = emptyArray<String>()

    fun add_atividade(atividade: String){

        atividades_inscritas += atividade
    }

    override fun bemVindo() {
        //to do
    }
}

abstract class Atividade(id_atividade: Int, local: String, dia: Int, horario: Int, membros_comissao: Array<MembroComissao>, num_calouros: Int, descricao:String ){


}

