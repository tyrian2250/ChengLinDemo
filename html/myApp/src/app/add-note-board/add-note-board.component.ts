import { Component } from '@angular/core';
import { ToolService } from '../tool.service';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


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
    this.tool.doPost('addNoteBoard',
      {
        title: this.title,
        content: this.content
      },
      (successResult: any) => {
        this.title = '';
        this.content = '';
        this.tool.openDialog();
        console.log(successResult);
      },
      (failedResult: any) => {
        console.log(failedResult);
      }
    );
  }

}
