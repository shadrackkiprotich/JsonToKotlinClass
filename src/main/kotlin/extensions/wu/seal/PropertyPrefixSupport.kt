package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.*
import javax.swing.JPanel

object PropertyPrefixSupport : Extension() {

    private const val prefixKeyEnable = "wu.seal.property_prefix_enable"
    private const val prefixKey = "wu.seal.property_prefix"

    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(prefixKey), getConfig(prefixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(prefixKeyEnable).toBoolean()) {
                    setConfig(prefixKey, text)
                }
            }
            document = NamingConventionDocument(80)
        }

        return jHorizontalLinearLayout {
            jCheckBox("Prefix append before every property: ", getConfig(prefixKeyEnable).toBoolean()) {
                addActionListener {
                    setConfig(prefixKeyEnable, isSelected.toString())
                    prefixJField.isEnabled = isSelected
                }
            }
            add(prefixJField)
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(prefixKeyEnable).toBoolean() && getConfig(prefixKey).isNotEmpty()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val prefix = getConfig(prefixKey)
                if (it.name.isNotEmpty()) {
                    val newName = prefix + it.name.first().toUpperCase() + it.name.substring(1)
                    it.copy(name = newName)
                } else it
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }

    }
}