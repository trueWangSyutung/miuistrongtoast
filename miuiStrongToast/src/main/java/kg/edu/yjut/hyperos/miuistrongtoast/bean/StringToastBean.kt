package kg.edu.yjut.hyperos.miuistrongtoast.bean



data class StringToastBean(
    private var left: Left? = null ,
    private var right: Right? = null
){
    data class Left(
        private var iconParams: IconParams? = null,
        private var textParams: TextParams? = null
    )

    data class Right(
        private var iconParams: IconParams? = null,
        private var textParams: TextParams? = null
    )

    data class IconParams(
        private var category: String? = null,
                private var iconFormat: String? = null,
                private var iconResName: String? = null,
                private var iconType: Int = 0
    )
    class TextParams (
        private var text: String? = null,
        private var textColor:Int = 0
    )
}
