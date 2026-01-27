rootProject.name = "navigator-service"

include(":web")
include(":ejb")

project(":web").projectDir = file("modules/web")
project(":ejb").projectDir = file("modules/ejb")