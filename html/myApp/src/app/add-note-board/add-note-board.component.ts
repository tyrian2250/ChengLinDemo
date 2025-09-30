import { Component } from '@angular/core';
import { ToolService } from '../tool.service';
import { MatDialog } from '@angular/material/dialog';
import { NoteBoardRequest, NoteBoardResponse } from '../models/note-board.interface';


@Component({
  selector: 'add-note-board',
  templateUrl: './add-note-board.component.html',
  styleUrls: ['./add-note-board.component.css']
})
export class AddNoteBoardComponent {

  constructor(
    private tool: ToolService,
    public dialog: MatDialog
  ) { }

  title: string = '';
  content: string = '';

  addNoteBoard() {
    this.tool.doPost<NoteBoardRequest, NoteBoardResponse>('addNoteBoard',
      {
        title: this.title,
        content: this.content
      },
      (successResult: NoteBoardResponse) => {
        this.title = '';
        this.content = '';
        this.tool.openDialog();
        console.log(successResult);
      },
      (failedResult: Error) => {
        console.log(failedResult);
      }
    );
  }

}
