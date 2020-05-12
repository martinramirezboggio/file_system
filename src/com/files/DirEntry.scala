package com.files

/**
 * Created by martinramirezboggio on 10/05/2020
 */
abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory

  def asFile: File

  def getType: String
}
