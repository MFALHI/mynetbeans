/*
 * Copyright 2015 Manikantan Narender Nath.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pr.nb.plugins.usd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.pr.nb.plugins.usd.EditWithSystem"
)
@ActionRegistration(
        displayName = "#CTL_EditWithSystem"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools/Desktop"),
    @ActionReference(path = "Loaders/folder/any/Actions/Desktop"),
    @ActionReference(path = "Editors/Popup/Desktop"),
    @ActionReference(path = "Editors/TabActions/Desktop"),
    @ActionReference(path = "Projects/Actions/Desktop"),
    @ActionReference(path = "Projects/packages/Actions/Desktop"),
    @ActionReference(path = "Loaders/Desktop")
})

@Messages({"CTL_EditWithSystem=Edit with desktop",
    "# {0} - the file to be edited",
    "CTL_Editing_Status=Editing file {0} using desktop",
    "# {0} - the file to be opened",
    "# {1} - the reasonf for error",
    "CTL_Editing_Error=Error opening desktop editor for {0}: {1}"})
public final class EditWithSystem implements ActionListener {

    private final DataObject context;

    public EditWithSystem(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            FileObject file = DesktopHelper.getInstance().getFileObjectFromContext(context);
            String msg = Bundle.CTL_Editing_Status(file.getPath());
            StatusDisplayer.getDefault().setStatusText(msg);
            DesktopHelper.getInstance().editWithDesktop(context);
        } catch (IOException ex) {
            FileObject file = DesktopHelper.getInstance().getFileObjectFromContext(context);
            String msg = Bundle.CTL_Editing_Error(file.getPath(), ex.getLocalizedMessage());
            StatusDisplayer.getDefault().setStatusText(msg);
            Exceptions.printStackTrace(ex);
        }
    }
}
