package com.commands
import com.files.{DirEntry, File}
import com.filesystem.State

/**
 * Created by martinramirezboggio on 12/05/2020
 */
class Touch(name: String) extends CreateEntry(name){
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workingDirectory.parentPath, name)
}
