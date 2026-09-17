package com.example.trashclassifier

data class TrashInfo(
    val nombreEs: String,
    val contenedor: String,
    val consejo: String,
    val confidence: Float = 0f
) {
    companion object {
        fun getInfo(category: String): TrashInfo {
            return when (category) {
                "carton" -> TrashInfo(
                    "Cartón",
                    "Contenedor AZUL (Papel y Cartón)",
                    "Dóblalo o aplástalo para que ocupe menos espacio. No debe tener grasa ni restos de comida."
                )
                "vidrio" -> TrashInfo(
                    "Vidrio",
                    "Contenedor VERDE (Envases de Vidrio)",
                    "Retira las tapas o corchos antes de tirarlo. No incluye espejos, cristales de ventanas ni bombillas."
                )
                "metal" -> TrashInfo(
                    "Metal",
                    "Contenedor AMARILLO (Envases y Metales)",
                    "Lata de refresco, conservas o papel aluminio limpio. Enjuágalos un poco para evitar malos olores."
                )
                "papel" -> TrashInfo(
                    "Papel",
                    "Contenedor AZUL (Papel y Cartón)",
                    "Folios, periódicos, revistas o sobres. No incluyas papel encerado, plastificado ni pañuelos sucios."
                )
                "plastico" -> TrashInfo(
                    "Plástico",
                    "Contenedor AMARILLO (Envases de Plástico)",
                    "Botellas, envases de yogur, bolsas. En la medida de lo posible, aplástalos para optimizar el espacio."
                )
                "organico" -> TrashInfo(
                    "Orgánico",
                    "Contenedor MARRÓN (Orgánico) / Restos",
                    "Restos de comida, cáscaras de fruta y verdura, posos de café. Evita mezclar con plásticos o bolsas no compostables."
                )
                else -> TrashInfo("Desconocido", "No identificado", "Intenta con otra foto más clara.")
            }
        }
    }
}
