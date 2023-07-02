package ge.giosan777.matutu.mbasemobile.models

data class Journal(val number: String, var type: Int, val date: Long, val duration: Long){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Journal

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }
}