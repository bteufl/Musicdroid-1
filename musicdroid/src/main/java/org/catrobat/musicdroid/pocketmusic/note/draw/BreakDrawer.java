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

package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

public class BreakDrawer extends SymbolDrawer {

    public static final int QUARTER_BREAK_HEIGHT = 3;
    public static final int EIGHT_BREAK_HEIGHT = 2;
    public static final int SIXTEENTH_BREAK_HEIGHT = 4;

    private SymbolDotDrawer symbolDotDrawer;

    public BreakDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        super(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);

        symbolDotDrawer = new SymbolDotDrawer(noteSheetCanvas, paint, distanceBetweenLines);
    }

    @Override
    public void drawSymbol(Symbol symbol) {
        if (false == (symbol instanceof BreakSymbol)) {
            throw new IllegalArgumentException("Symbol is not of type BreakSymbol: " + symbol);
        }

        drawBreak(((BreakSymbol) symbol).getNoteLength());
    }

    private void drawBreak(NoteLength noteLength) {
        Rect breakRect;

        if (noteLength.isHalfOrHigher()) {
            breakRect = drawBreakBar(noteLength);
        } else {
            breakRect = drawBreakBitmap(noteLength);
        }

        if (noteLength.hasDot()) {
            symbolDotDrawer.drawDot(breakRect);
        }
    }

    private Rect drawBreakBitmap(NoteLength noteLength) {
        int breakHeight = distanceBetweenLines;
        int breakId = 0;

        if (noteLength == NoteLength.QUARTER || noteLength == NoteLength.QUARTER_DOT) {
            breakHeight *= QUARTER_BREAK_HEIGHT;
            breakId = R.drawable.break_4;
        } else if (noteLength == NoteLength.EIGHT || noteLength == NoteLength.EIGHT_DOT) {
            breakHeight *= EIGHT_BREAK_HEIGHT;
            breakId = R.drawable.break_8;
        } else if (noteLength == NoteLength.SIXTEENTH) {
            breakHeight *= SIXTEENTH_BREAK_HEIGHT;
            breakId = R.drawable.break_16;
        }

        int startXPositionBreak = getCenterPointForNextSymbol().x;

        Rect breakRect = noteSheetCanvas.drawBitmap(resources, breakId, breakHeight, startXPositionBreak, noteSheetCanvas.getHeightHalf());
        drawPosition.setStartXPositionForNextElement(breakRect.right);

        return breakRect;
    }

    private Rect drawBreakBar(NoteLength noteLength) {
        Rect breakRect = new Rect();
        Point centerPoint = getCenterPointForNextSymbol();
        int breakWidthHalf = distanceBetweenLines / 2;

        breakRect.left = centerPoint.x - breakWidthHalf;
        if ((NoteLength.HALF == noteLength) || (NoteLength.HALF_DOT == noteLength)) {
            breakRect.top = centerPoint.y - breakWidthHalf;
        } else {
            breakRect.top = centerPoint.y + breakWidthHalf;
        }
        breakRect.right = centerPoint.x + breakWidthHalf;
        breakRect.bottom = centerPoint.y;

        noteSheetCanvas.drawRect(breakRect, paint);

        return breakRect;
    }
}
