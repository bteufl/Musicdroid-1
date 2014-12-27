/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.graphics.PointF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteFlagDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;

public class NoteFlagDrawerTest extends AbstractDrawerTest {

    private NoteFlagDrawer noteFlagDrawer;
    private PointF endPointOfNoteStem;
    private MusicalKey key;

    @Override
    protected void setUp() {
        super.setUp();

        noteFlagDrawer = new NoteFlagDrawer(noteSheetCanvas, paint, distanceBetweenLines);
        endPointOfNoteStem = new PointF(1000, 1000);
        key = MusicalKey.VIOLIN;
    }

    public void testDrawFlag1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.EIGHT);
        noteFlagDrawer.drawFlag(endPointOfNoteStem, noteSymbol, key);

        assertCanvasElementQueuePath();
    }

    public void testDrawFlag2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.EIGHT_DOT);
        noteFlagDrawer.drawFlag(endPointOfNoteStem, noteSymbol, key);

        assertCanvasElementQueuePath();
    }

    public void testDrawFlag3() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.SIXTEENTH);
        noteFlagDrawer.drawFlag(endPointOfNoteStem, noteSymbol, key);

        assertCanvasElementQueuePath();
        assertCanvasElementQueuePath();
    }
}
