package com.files

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class Directory(override val parentPath:String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {


}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("","")

  def empty(parentPath: String, name:String) : Directory = new Directory(parentPath, name, List())
}
