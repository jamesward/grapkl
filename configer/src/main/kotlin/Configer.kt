import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.JavaApplication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.pkl.config.java.ConfigEvaluator
import org.pkl.config.kotlin.forKotlin
import org.pkl.config.kotlin.to
import org.pkl.core.ModuleSource
import java.io.File

class Configer : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.gradle.beforeProject {
            val buildFile = File(project.rootDir, "build.gradle.pkl")

            val config = ConfigEvaluator.preconfigured().forKotlin().use { evaluator ->
                evaluator.evaluate(ModuleSource.file(buildFile))
            }

            val product = config["product"].to<String>()

            if (product == "jvm/app") {
                apply(plugin = "java")
                apply(plugin = "application")

                extensions.configure<JavaApplication> {
                    // yeah this shouldn't be hard coded
                    mainClass.set("Main")
                }
            }

            /*
            tasks.create("createConfig") {
                val buildFile = File(project.rootDir, "build.gradle.pkl")
                val sourceModules = listOf(buildFile.toURI())
                val baseOptions = CliBaseOptions(sourceModules)

                val options = CliKotlinCodeGeneratorOptions(
                    base = baseOptions,
                    outputDir = project.rootDir.toPath(),
                )

                CliKotlinCodeGenerator(options).run()

            }
             */

        }
    }
}
