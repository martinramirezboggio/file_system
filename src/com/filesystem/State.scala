package com.filesystem

import com.files.Directory

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class State(val root: Directory, val workingDirectory: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String) : State =
    State(root, workingDirectory,message)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, workingDirectory: Directory, output: String = ""): State =
    new State(root, workingDirectory, output)
}
