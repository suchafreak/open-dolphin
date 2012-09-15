/*
 * Copyright 2012 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.canoo.dolphin.demo

import com.canoo.dolphin.core.comm.Command
import com.canoo.dolphin.core.comm.CreatePresentationModelCommand
import com.canoo.dolphin.core.server.ServerAttribute
import com.canoo.dolphin.core.server.ServerPresentationModel

def config = new JavaFxInMemoryConfig()
def dolphin = config.serverDolphin

dolphin.action "saveNewSelectedPerson", { cmd, List<Command> response ->
    def selectedPerson = dolphin.findPresentationModelById('selectedPerson')

    // here: store a new person domain object with the attributes from above and get a new persistent id in return
    def pmId = "person-1" // for demo purposes assume a fixed value

    def newAttributes = selectedPerson.attributes.collect {
        new ServerAttribute(propertyName: it.propertyName, initialValue: it.value, qualifier: "${pmId}.${it.propertyName}")
    }
    response << CreatePresentationModelCommand.makeFrom(new ServerPresentationModel(pmId, newAttributes))
}

new NewAndSaveView().show(config.clientDolphin)