package toyLanguage.controller;

import toyLanguage.domain.prg_state.PrgState;

public interface ExecutionObserver {
    void onExecutionStart(PrgState initialState);
    void onStepExecuted(PrgState currentState);
    void onExecutionFinish(PrgState finalState);
}
