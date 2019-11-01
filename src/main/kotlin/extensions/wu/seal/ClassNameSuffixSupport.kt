package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.*
import wu.seal.jsontokotlin.utils.getChildType
import wu.seal.jsontokotlin.utils.getRawType
import javax.swing.JPanel

object ClassNameSuffixSupport : Extension() {

    private const val suffixKeyEnable = "wu.seal.class_name_suffix_enable"
    private const val suffixKey = "wu.seal.class_name_suffix"

    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(suffixKey), getConfig(suffixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(suffixKeyEnable).toBoolean()) {
                    setConfig(suffixKey, text)
                }
            }
            document = NamingConventionDocument(80)
        }

        return jHorizontalLinearLayout {
            jCheckBox("Suffix append after every class name: ", getConfig(suffixKeyEnable).toBoolean()) {
                addActionListener {
                    setConfig(suffixKeyEnable, isSelected.toString())
                    prefixJField.isEnabled = isSelected
                }
            }
            add(prefixJField)
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val suffix = getConfig(suffixKey)

        return if (getConfig(suffixKeyEnable).toBoolean() && suffix.isNotEmpty()) {
            val standTypes = listOf("Int", "Double", "Long", "String", "Boolean")
            val originName = kotlinDataClass.name
            val newPropertyTypes =
                    kotlinDataClass.properties.map {
                        val rawSubType = getChildType(getRawType(it.type))
                        when {
                            it.type.isMapType() -> {
                                it.type//currently don't support map type
                            }
                            standTypes.contains(rawSubType) -> it.type
                            else -> it.type.replace(rawSubType, rawSubType + suffix)
                        }
                    }

            val newPropertyDefaultValues = kotlinDataClass.properties.map {
                val rawSubType = getChildType(getRawType(it.type))
                when {
                    it.value.isEmpty() -> it.value
                    it.type.isMapType() -> {
                        it.value//currently don't support map type
                    }
                    standTypes.contains(rawSubType) -> it.value
                    else -> it.value.replace(rawSubType, rawSubType + suffix)
                }
            }

            val newProperties = kotlinDataClass.properties.mapIndexed { index, property ->

                val newType = newPropertyTypes[index]

                val newValue = newPropertyDefaultValues[index]

                property.copy(type = newType, value = newValue)
            }


            kotlinDataClass.copy(name = originName + suffix, properties = newProperties)

        } else {
            kotlinDataClass
        }

    }

    private fun String.isMapType(): Boolean {

        return matches(Regex("Map<.+,.+>"))
    }


}

