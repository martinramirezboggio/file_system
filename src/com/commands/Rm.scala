package com.commands
import com.files.Directory
import com.filesystem.State

import scala.annotation.tailrec

/**
 * Created by martinramirezboggio on 15/05/2020
 */
class Rm (name: String) extends Command {
  override def apply(state: State): State = {
    // 1. Get working directory
    val workingDirectory = state.workingDirectory

    // 2. Get abs Path
    val absolutePath =
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (workingDirectory.isRoot) workingDirectory.path + name
      else workingDirectory.path + Directory.SEPARATOR + name

    // 3. Do some checks
    if (Directory.ROOT_PATH.equals(absolutePath)) state.setMessage("Nuclear war not supported yet")
    else
      doRm(state, absolutePath)
  }

    def doRm(state: State, path: String): State = {

      def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
        if (path.isEmpty) currentDirectory
        else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
        else {
          val nextDirectory = currentDirectory.findEntry(path.head)
          if (!nextDirectory.isDirectory) currentDirectory
          else {
            val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
            if (newNextDirectory == nextDirectory) currentDirectory
            else currentDirectory.replaceEntry(path.head, newNextDirectory)
          }
        }
      }
      // 4. Find entry to remove

      // 5. Update structure
      val tokens = path.substring(1).split(Directory.SEPARATOR).toList
      val newRoot: Directory = rmHelper(state.root, tokens)
      if (newRoot == state.root)
        state.setMessage(path + ": no such File or Directory")
      else
         State(newRoot, newRoot.findDescendant(state.workingDirectory.path.substring(1)))
    }
}
