gradle.projectsLoaded {
   rootProject {
      version = File("project.version").readText().trim()
   }
}