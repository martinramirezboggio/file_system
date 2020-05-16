package com.files

/**
 * Created by martinramirezboggio on 10/05/2020
 */
abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separatorIfNecessary =
      if(Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR

    parentPath + separatorIfNecessary + name
  }

  def asDirectory: Directory

  def asFile: File

  def getType: String

  def isDirectory: Boolean

  def isFile: Boolean
}
