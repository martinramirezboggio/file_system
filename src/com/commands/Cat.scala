package com.commands

import com.filesystem.State

/**
 * Created by martinramirezboggio on 16/05/2020
 */
class Cat(fileName: String) extends Command {

  //TODO support relative and abs path follow CD
  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory

    val dirEntry = workingDirectory.findEntry(fileName)

    if(dirEntry == null || !dirEntry.isFile)
      state.setMessage(fileName + ": no such File")
    else
      state.setMessage(dirEntry.asFile.contents)
  }

}
