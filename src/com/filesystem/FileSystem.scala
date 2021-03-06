package com.filesystem

import java.util.Scanner

import com.commands.Command
import com.files.Directory

/**
 * Created by martinramirezboggio on 10/05/2020
 */

object FileSystem extends App{

  val root = Directory.ROOT
//  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
//    currentState.show
//    Command.from(newLine).apply(currentState)
//  })
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true){
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
