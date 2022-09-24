package it.pasqualini.planetroid.audio;

import java.io.Serial;
import java.util.EventObject;

public class SoundTrackManager {
    public static class SoundTrackEvent extends EventObject {

        public SoundTrackEvent(Object source, SoundTrackClip.SoundTrack.Type type) {
            super(source);
            this.type = type;
        }


        @Serial
        private static final long serialVersionUID = -1274246333383880410L;


        private final SoundTrackClip.SoundTrack.Type type;


        public SoundTrackEvent(SoundTrackClip.SoundTrack s, SoundTrackClip.SoundTrack.Type type) {
            super(s);
            this.type = type;
        }


        public final SoundTrackClip.SoundTrack.Type getType() {
            return type;
        }


        public static class Type {

            /**
             * Type name.
             */
            private final String name;

            /**
             * Constructs a new event type.
             *
             * @param name name of the type
             */
            protected Type(String name) {
                this.name = name;
            }

            //$$fb 2002-11-26: fix for 4695001: SPEC: description of equals() method contains typo

            /**
             * Indicates whether the specified object is equal to this event type,
             * returning {@code true} if the objects are the same.
             *
             * @param obj the reference object with which to compare
             * @return {@code true} if the specified object is equal to this event
             * type; {@code false} otherwise
             */
            @Override
            public final boolean equals(Object obj) {
                return super.equals(obj);
            }

            /**
             * Returns a hash code value for this event type.
             *
             * @return a hash code value for this event type
             */
            @Override
            public final int hashCode() {
                return super.hashCode();
            }

            /**
             * Returns type's name as the string representation of the event type.
             *
             * @return a string representation of the event type
             */
            @Override
            public String toString() {
                return name;
            }


            public static final SoundTrackClip.SoundTrack.Type START = new SoundTrackClip.SoundTrack.Type("Start");
            public static final SoundTrackClip.SoundTrack.Type END = new SoundTrackClip.SoundTrack.Type("End");
            public static final SoundTrackClip.SoundTrack.Type STOP = new SoundTrackClip.SoundTrack.Type("Stop");


        }
    }


}
