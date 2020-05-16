package com.commands
import com.files.{DirEntry, Directory}
import com.filesystem.State

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workingDirectory.path, name)
}
