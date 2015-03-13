package com.amazon.infra.commandbus;


public interface CommandHandler<T extends Command<R>, R> {
	public R handle(T command) throws CommandException;
	Class<T> getCommandType();
	Class<R> getReturnType();
}
