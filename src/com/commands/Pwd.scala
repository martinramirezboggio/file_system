package com.commands
import com.filesystem.State

/**
 * Created by martinramirezboggio on 12/05/2020
 */
class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.workingDirectory.path)

}
