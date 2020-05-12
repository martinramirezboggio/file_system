package com.commands
import com.files.{DirEntry, Directory}
import com.filesystem.State

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class Mkdir(name: String) extends Command {
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
      doMkdir(name, state)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doMkdir(name: String, state:State) : State = {

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
    val newDirectory = Directory.empty(workingDirectory.parentPath, name)
    // 3. update the whole directory structure starting from root
    val newRoot = updateStructure(state.root, allDirsInPath, newDirectory)
    // 4. find new working directory instance given working directory full path
    val newWorkingDirectory = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWorkingDirectory)

  }
}
