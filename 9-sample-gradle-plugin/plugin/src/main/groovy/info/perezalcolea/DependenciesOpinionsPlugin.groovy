package info.perezalcolea

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ComponentMetadataContext
import org.gradle.api.artifacts.ComponentMetadataRule
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.plugins.JavaPlugin

class DependenciesOpinionsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.withType(JavaPlugin).configureEach {
            configureJavaAssist(project)
            configureAlignment(project)
        }
    }

    private void configureJavaAssist(Project project) {
        project.configurations.all { Configuration configuration ->
            configuration.resolutionStrategy.capabilitiesResolution.withCapability("org.javassist:javassist") {
                def toBeSelected = candidates.find {
                    it.id instanceof ModuleComponentIdentifier
                        && it.id.group == 'org.javassist'
                        && it.id.module == 'javassist'
                }
                if (toBeSelected != null) {
                    select(toBeSelected)
                }
                because 'org.javassist over javassist groupID'
            }
        }
        project.dependencies.components.all(JavassistCapability)
    }

    private void configureAlignment(Project project) {
        project.dependencies.components.all(MyAlignmentRule)
    }

    static abstract class MyAlignmentRule implements ComponentMetadataRule {
        void execute(ComponentMetadataContext ctx) {
            ctx.details.with {
                if (id.group.startsWith("info.perezalcolea")) {
                    // declare that Perezalcolea modules all belong to the virtual platform
                    belongsTo("info.perezalcolea:perezalcolea-virtual-platform:${id.version}")
                }
            }
        }
    }
    static class JavassistCapability implements ComponentMetadataRule {
        void execute(ComponentMetadataContext context) {
            context.details.with {
                if (id.group == "javassist" && id.name == "javassist") {
                    allVariants {
                        it.withCapabilities {
                            // Declare that javassist provides the org.javassist capability, but with an older version
                            it.addCapability("org.javassist", "javassist", id.version)
                        }
                    }
                }
            }
        }
    }

}
