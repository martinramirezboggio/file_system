package com.files

import com.filesystem.FilesystemException
import com.sun.tools.javac.file.RelativePath

import scala.annotation.tailrec

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class Directory(override val parentPath:String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]) : DirEntry =
      if(contentList.isEmpty) null
      else if(contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name,contentList.tail)

    findEntryHelper(entryName, contents)
  }

  def addEntry(newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents :+ newEntry) //append for List
  }

  def hasEntry(name: String): Boolean = findEntry(name) != null

  def getAllFoldersInPath: List[String] = {
    // /a/b/c => List["a","b","c"]
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

  def findDescendant(path: List[String]): Directory = {
    if(path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }

  def findDescendant(relativePath: String): Directory =
    if(relativePath.isEmpty) this
    else findDescendant(relativePath.split(Directory.SEPARATOR).toList)

  def removeEntry(entryName:String): Directory =
    if(!hasEntry(entryName)) this
    else new Directory(parentPath, name, contents.filter(x => !x.name.equals(entryName)))

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents.filter(x => ! x.name.equals(entryName)) :+ newEntry)
  }

  override def getType: String = "Directory"

  override def asDirectory: Directory = this

  def isRoot: Boolean = parentPath.isEmpty

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false

  override def asFile: File = throw new FilesystemException("A directory can not be converted to a file")
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("","")

  def empty(parentPath: String, name:String) : Directory = new Directory(parentPath, name, List())
}
