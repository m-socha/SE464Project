export function getNotebook(state, notebookID) {
  return state.notebook.byId[notebookID];
}
