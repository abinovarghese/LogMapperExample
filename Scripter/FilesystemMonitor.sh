#!/bin/sh
MONITORDIR="landingzone"
inotifywait -m -r  -e move  -e create --format '%e %w %f' "${MONITORDIR}" | while read NEWFILE
do
		date=$(date +'%Y%m%d')
		filename="FileTrackerLog_$date"
        echo "$(date +%s) :: $NEWFILE" >>  "$filename"
done 