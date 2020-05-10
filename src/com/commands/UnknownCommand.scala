package com.commands
import com.filesystem.State

/**
 * Created by martinramirezboggio on 10/05/2020
 */
class UnknownCommand extends Command {

  override def apply(state: State): State =
    state.setMessage("Command not found!")
}
