package com.commands

import com.filesystem.State

/**
 * Created by martinramirezboggio on 10/05/2020
 */
trait Command {

  def apply(state: State): State
}

object Command {

  def from(input: String): Command =
    new UnknownCommand
}
