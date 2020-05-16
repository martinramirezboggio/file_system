package com.commands
import apple.laf.JRSUIConstants.Direction
import com.files.{Directory, File}
import com.filesystem.State

import scala.annotation.tailrec

/**
 * Created by martinramirezboggio on 16/05/2020
 */
class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
    /*
    if no args => state
    if one args => print to console
    else if multiple args
    {
      operator = next to last argument
      if >
        echo to a File (create File if not there)
      if >>
        append to a file
       else
        just echo everything in the console
    }
     */

    if(args.isEmpty) state
    else if(args.length == 1) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val fileName = args(args.length - 1)
      val contents = createContent(args, args.length - 2)

      if(">>".equals(operator)) doEcho(state, contents, fileName, append = true)
      else if(">".equals(operator)) doEcho(state, contents, fileName, append = false)
      else state.setMessage(createContent(args, args.length))
    }
  }

  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory ={
    if(path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)
      if (dirEntry == null)
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if (dirEntry.isDirectory) currentDirectory
      else
        if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
    }else {
        val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
        val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)

        if(newNextDirectory == nextDirectory) currentDirectory
        else currentDirectory.replaceEntry(path.head, newNextDirectory)
      }
  }

  def doEcho(state: State, contents: String, fileName: String, append: Boolean):State = {
    if(fileName.contains(Directory.SEPARATOR))
      state.setMessage("Echo: Filename must not contain separators")
    else {
      val newRoot : Directory = getRootAfterEcho(state.root, state.workingDirectory.getAllFoldersInPath :+ fileName, contents, append)
      if(newRoot == state.root)
        state.setMessage(fileName + ": no such File")
      else
        State(newRoot, newRoot.findDescendant(state.workingDirectory.getAllFoldersInPath))
    }

  }


  // TopIndex non-inclusive
  def createContent(args: Array[String], topIndex: Int): String = {

    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      if(currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator + " " + args(currentIndex))
    }
    createContentHelper(0, "")
  }
}
