package com.commands
import com.files.DirEntry
import com.filesystem.State

/**
 * Created by martinramirezboggio on 11/05/2020
 */

class Ls extends Command {
  override def apply(state: State): State = {
    val contents = state.workingDirectory.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }

  def createNiceOutput(contents: List[DirEntry]): String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head

      entry.name + "[" + entry.getType + "] \n" + createNiceOutput(contents.tail)
    }
  }
}
