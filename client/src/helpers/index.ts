export function parseExtendedIsoToHuman(dateTimeStr: string) {
  // Regular expression to match: [+|-]YYYYYY-MM-DDTHH:mm:ssZ
  const regex =
    /^([+-])(\d{4,})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(?:\.(\d+))?(Z|[+-]\d{2}:\d{2})$/;
  const match = dateTimeStr.trim().match(regex);

  if (!match) {
    throw new Error('Invalid extended ISO 8601 format');
  }

  const [
    ,
    // full match
    sign,
    year,
    month,
    day,
    hour,
    minute,
    second,
    millisecond = '0',
    timezone,
  ] = match;

  if (!sign || !year || !month || !day) {
    return 'unparsed date';
  }

  // Construct signed year
  const signedYear = sign + year;

  // Format month and day names
  const monthNames = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December',
  ];
  const monthName = monthNames[parseInt(month, 10) - 1];

  // Pad milliseconds to 3 digits
  const ms = millisecond.padEnd(3, '0').slice(0, 3);

  // Format timezone info
  let tzDisplay = 'UTC';
  if (timezone !== 'Z') {
    tzDisplay = `UTC${timezone}`;
  }

  // Build human-readable string
  return (
    `${monthName} ${parseInt(day)}${getDaySuffix(day)}, ${signedYear} ` +
    `${hour}:${minute}:${second}.${ms} ${tzDisplay}`
  );
}

function getDaySuffix(day: string) {
  const d = parseInt(day, 10);
  if (d >= 11 && d <= 13) return 'th';
  switch (d % 10) {
    case 1:
      return 'st';
    case 2:
      return 'nd';
    case 3:
      return 'rd';
    default:
      return 'th';
  }
}
