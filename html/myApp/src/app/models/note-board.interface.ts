export interface NoteBoard {
  id: number;
  title: string;
  content: string;
  timestamp_without_timezone?: string;
  expanded?: boolean;
  checkToDelete?: boolean;
}

export interface NoteBoardResponse {
  Data: NoteBoard[];
  Message: string;
  Status: string;
}

export interface NoteBoardRequest {
  title?: string;
  content?: string;
  idArray?: number[];
}
