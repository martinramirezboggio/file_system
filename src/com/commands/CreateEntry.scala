package com.commands

import com.files.{DirEntry, Directory}
import com.filesystem.State

/**
 * Created by martinramirezboggio on 12/05/2020
 */
abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {

    val workingDirectory = state.workingDirectory

    if(workingDirectory.hasEntry(name)){
      state.setMessage(s"Entry $name already exists!")
    }else if(name.contains(Directory.SEPARATOR)){
      //CAN'T do mkdir -p something/something -- maybe later
      state.setMessage(s"$name must not contain separators!")
    }else if(checkIllegal(name)) {
      state.setMessage(s"$name : illegal entry name!")
    }else {
      doCreateEntry(name, state)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doCreateEntry(name: String, state:State) : State = {

    def updateStructure(currentDirectory: Directory, path:List[String], newEntry: DirEntry): Directory = {
      if(path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val workingDirectory = state.workingDirectory

    // 1. all the directories in the full path
    val allDirsInPath = workingDirectory.getAllFoldersInPath
    // 2. create new directory in the working directory
    // TODO implement this
    val newDirectory : DirEntry = createSpecificEntry(state)
    // 3. update the whole directory structure starting from root
    val newRoot = updateStructure(state.root, allDirsInPath, newDirectory)
    // 4. find new working directory instance given working directory full path
    val newWorkingDirectory = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWorkingDirectory)

  }

  def createSpecificEntry(state: State): DirEntry
}
