package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
class AdvancedPropertyTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {

    init {

        jScrollPanel(JBDimension(500, 300)) {
            jVerticalLinearLayout {
                jLabel("Keyword")
                jButtonGroup {
                    jRadioButton("Val", !ConfigManager.isPropertiesVar) {
                        addActionListener {
                            ConfigManager.isPropertiesVar = false
                        }
                    }
                    jRadioButton("Var", ConfigManager.isPropertiesVar) {
                        addActionListener {
                            ConfigManager.isPropertiesVar = true
                        }
                    }
                }
                jLine()
                jLabel("Type")
                jButtonGroup {
                    jRadioButton("Non-Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.NotNullable) {
                        addActionListener {
                            ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.NotNullable
                        }
                    }
                    jRadioButton("Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable) {
                        addActionListener {
                            ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable
                        }
                    }
                    jRadioButton("Auto Determine Nullable Or Not From JSON Value", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot) {
                        addActionListener {
                            ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot
                        }
                    }
                }
                jLine()
                jLabel("Default Value Strategy")
                jButtonGroup {
                    jRadioButton("Don't Init With Default Value", ConfigManager.defaultValueStrategy == DefaultValueStrategy.None) {
                        addActionListener {
                            ConfigManager.defaultValueStrategy == DefaultValueStrategy.None
                        }
                    }
                    jRadioButton("Init With Non-Null Default Value (Avoid Null)", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull) {
                        addActionListener {
                            ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull
                        }
                    }
                    jRadioButton("Init With Default Value Null When Property Is Nullable", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AllowNull) {
                        addActionListener {
                            ConfigManager.defaultValueStrategy == DefaultValueStrategy.AllowNull
                        }
                    }
                }
            }
        }
    }
}
